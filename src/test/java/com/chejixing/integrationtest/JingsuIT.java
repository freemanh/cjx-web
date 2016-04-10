package com.chejixing.integrationtest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

public class JingsuIT extends BaseSpringIT {
	private int portOfJingsu;

	@Test
	@Transactional
	public void test() throws InterruptedException {
		jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS mondata_jingsu66011 LIKE mondata");

		ByteBuffer buf = ByteBuffer.allocate(1024);
		// 00 03 12 00 24 00 01 01 DB 00 A0 03 52 00 8E 00 13 00 F0 00 00 A1 15
		// 101dbxxxx
		buf.put(new byte[] { 0x00, 0x03, 0x12, 0x00, 0x24, 0x00, 0x01, 0x01, (byte) 0xdb, 0x00, (byte) 0xa0, 0x03, 0x52, 0x00, (byte) 0x8e, 0x00, 0x13, 0x00, (byte) 0xf0, 0x00, 0x00, (byte) 0xa1,
				0x15 });
		buf.flip();

		try {
			SocketChannel socket = SocketChannel.open(new InetSocketAddress(portOfJingsu));
			ByteBuffer resp = ByteBuffer.allocate(100);
			socket.read(resp);
			
			while (buf.remaining() > 0) {
				socket.write(buf);
			}
			
			resp.clear();
			socket.read(resp);
			Thread.sleep(5000);
			
			socket.close();
		} catch (IOException e) {
			fail("connect to socket server failed!");
		}

		long count = JdbcTestUtils.countRowsInTable(jdbcTemplate, "mondata_jingsu66011");
		assertEquals(1, count);
	}

	@Value("${port.jingsu}")
	public void setPortOfJingsu(int portOfJingsu) {
		this.portOfJingsu = portOfJingsu;
	}

}
