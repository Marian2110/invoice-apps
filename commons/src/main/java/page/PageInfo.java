package page;

import lombok.Builder;

@Builder
public record PageInfo(
        int page,
        int size,
        int totalPages,
        int totalSize
) {
}
