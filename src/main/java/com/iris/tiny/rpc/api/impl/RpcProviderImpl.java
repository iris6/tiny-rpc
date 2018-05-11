package com.iris.tiny.rpc.api.impl;
import com.iris.tiny.rpc.model.RpcRequest;
import com.iris.tiny.rpc.model.ZkData;
import com.iris.tiny.rpc.serializer.RpcDecoder;
import com.iris.tiny.rpc.tool.Tool;
import com.iris.tiny.rpc.zk.ZkClient;
import com.iris.tiny.rpc.zk.impl.ZkClientImpl;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.HashMap;
import java.util.Map;


import com.iris.tiny.rpc.api.RpcProvider;
import com.iris.tiny.rpc.model.RpcResponse;
import com.iris.tiny.rpc.serializer.RpcEncoder;
import org.apache.zookeeper.CreateMode;

public class RpcProviderImpl extends RpcProvider {

	//存放接口名与服务对象之间的映射关系
	private Map<String, Object> handlerMap = new HashMap<>();
	
	private Class<?> interfaceclazz;
	private Object classimplement;
	private String version;
	public String getVersion() {
		return version;
	}

	public int getTimeout() {
		return timeout;
	}

	public String getType() {
		return type;
	}

	private int timeout;
	private String type;
	@Override
	public RpcProvider serviceInterface(Class<?> serviceInterface) {
		// TODO Auto-generated method stub
		this.interfaceclazz=serviceInterface;
		return this;
	}

	@Override
	public RpcProvider version(String version) {
		// TODO Auto-generated method stub
		this.version=version;
		return this;
	}

	@Override
	public RpcProvider impl(Object serviceInstance) {
		this.classimplement=serviceInstance;
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public RpcProvider timeout(int timeout) {
		// TODO Auto-generated method stub
		this.timeout=timeout;
		return this;
	}

	@Override
	public RpcProvider serializeType(String serializeType) {
		// TODO Auto-generated method stub
		this.type=serializeType;
		return this;
	}

	/**
	 * 发布RPC服务，后面用ZooKeeper，前期用普通的
	 */
	@Override
	public void publish() {
		handlerMap.put(interfaceclazz.getName(), classimplement);
		ZkHandler("127.0.0.1","8080",interfaceclazz.getName());
		// TODO Auto-generated method stub
		EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            // server端采用简洁的连写方式，client端才用分段普通写法。
           serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
           .handler(new LoggingHandler(LogLevel.INFO))
           .childHandler(new ChannelInitializer<SocketChannel>() {
                           @Override
                           public void initChannel(SocketChannel ch)
                                    throws Exception {
                            ch.pipeline().addLast(new RpcEncoder(RpcResponse.class));
                            ch.pipeline().addLast(new RpcDecoder(RpcRequest.class));
//                        	ch.pipeline().addLast(new FSTNettyEncode());
//                            ch.pipeline().addLast(new FSTNettyDecode());
                           	ch.pipeline().addLast(new RpcRequestHandler(handlerMap));
                          }
                     })
                     .option(ChannelOption.SO_KEEPALIVE , true )
                     .childOption(ChannelOption.TCP_NODELAY, true)
                     .option(ChannelOption.SO_SNDBUF, 1024)
                     .option(ChannelOption.SO_RCVBUF, 2048);
           
           
           ChannelFuture f = serverBootstrap.bind(8080).sync();
           f.channel().closeFuture().sync();
       } catch (InterruptedException e) {
       } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
       }
	 }

	public void ZkHandler(String ip,String port,String serviceName){
		ZkClient zk=new ZkClientImpl("0.0.0.0:2181");
		ZkData zkData=new ZkData();
		zkData.setIP(ip); zkData.setPort(port); zkData.setServiceName(serviceName);
		zk.setData("/iris", Tool.serialize(zkData),-1, CreateMode.EPHEMERAL);
		ZkClientImpl.addService(zk.getData("/iris",-1,true));
	}

}
