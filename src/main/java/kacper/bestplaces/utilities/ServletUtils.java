package kacper.bestplaces.utilities;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class ServletUtils {
    public static final String BASE_URL = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
}
