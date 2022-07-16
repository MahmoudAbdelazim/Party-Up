package com.partyup.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;
import org.yaml.snakeyaml.util.ArrayUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
class FileCompressionUtilityTest {

	private byte[] data;

	@BeforeEach
	void setUp() {
		data = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
	}

	@Test
	void compressFile() {
		byte[] bytes = FileCompressionUtility.decompressFile(FileCompressionUtility.compressFile(data));
		ArrayList<Byte> compressed = new ArrayList<>();
		for (byte b:bytes) compressed.add(b);
		ArrayList<Byte> wrappedData = new ArrayList<>();
		for (byte b:data) wrappedData.add(b);
		assertEquals(compressed, wrappedData);
	}
}