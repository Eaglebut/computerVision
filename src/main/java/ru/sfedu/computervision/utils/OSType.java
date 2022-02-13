package ru.sfedu.computervision.utils;

import lombok.Getter;
import static ru.sfedu.computervision.Constants.PATH_TO_NATIVE_LIB_LINUX;
import static ru.sfedu.computervision.Constants.PATH_TO_NATIVE_LIB_WIN;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public enum OSType {
	MACOS(Arrays.asList("mac", "darwin"), ""),
	WINDOWS(List.of("win"), PATH_TO_NATIVE_LIB_WIN),
	LINUX(List.of("nux"), PATH_TO_NATIVE_LIB_LINUX),
	OTHER(new ArrayList<>(), "");

	private final List<String> classifiers;
	private final String constantPath;

	OSType(List<String> classifiers, String constantPath) {
		this.classifiers = classifiers;
		this.constantPath = constantPath;
	}

	public String getConstantPath() throws Exception {
		if (constantPath.isEmpty()) {
			throw new Exception("%s does not supports!!!!!".formatted(this.toString()));
		}
		return constantPath;
	}

	public static OSType getOperatingSystemType(String type) {
		return Arrays.stream(values())
				.filter(osType -> osType.getClassifiers().stream().anyMatch(type::contains))
				.findAny()
				.orElse(OTHER);
	}
}
