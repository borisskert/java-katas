package paginationhelper;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PaginationHelperTest {

    @Test
    void shouldProvideItemCount() throws Exception {
        assertThat(new PaginationHelper<>(List.of(1, 2, 3, 4, 5), 1).itemCount()).isEqualTo(5);
        assertThat(new PaginationHelper<>(List.of("Hello", "World"), 1).itemCount()).isEqualTo(2);
    }

    @Test
    void shouldProvidePageCount() throws Exception {
        assertThat(new PaginationHelper<>(List.of(1, 2, 3, 4, 5), 5).pageCount()).isEqualTo(1);
        assertThat(new PaginationHelper<>(List.of(1, 2, 3, 4, 5), 3).pageCount()).isEqualTo(2);
        assertThat(new PaginationHelper<>(List.of(1, 2, 3, 4, 5), 2).pageCount()).isEqualTo(3);
        assertThat(new PaginationHelper<>(List.of(1, 2, 3, 4, 5), 1).pageCount()).isEqualTo(5);
    }

    @Test
    void shouldProvidePageItemCount() throws Exception {
        assertThat(new PaginationHelper<>(List.of(1, 2, 3, 4, 5), 5).pageItemCount(0)).isEqualTo(5);
        assertThat(new PaginationHelper<>(List.of(1, 2, 3, 4, 5), 5).pageItemCount(1)).isEqualTo(-1);
        assertThat(new PaginationHelper<>(List.of(1, 2, 3, 4, 5), 3).pageItemCount(0)).isEqualTo(3);
        assertThat(new PaginationHelper<>(List.of(1, 2, 3, 4, 5), 3).pageItemCount(1)).isEqualTo(2);
        assertThat(new PaginationHelper<>(List.of(1, 2, 3, 4, 5), 3).pageItemCount(-1)).isEqualTo(-1);
        assertThat(new PaginationHelper<>(List.of(), 3).pageItemCount(0)).isEqualTo(-1);
    }

    @Test
    void shouldProvidePageIndex() throws Exception {
        assertThat(new PaginationHelper<>(List.of(1, 2, 3, 4, 5), 5).pageIndex(0)).isEqualTo(0);
        assertThat(new PaginationHelper<>(List.of(1, 2, 3, 4, 5), 3).pageIndex(0)).isEqualTo(0);
        assertThat(new PaginationHelper<>(List.of(1, 2, 3, 4, 5), 2).pageIndex(3)).isEqualTo(1);
        assertThat(new PaginationHelper<>(List.of(1, 2, 3, 4, 5), 2).pageIndex(8)).isEqualTo(-1);
        assertThat(new PaginationHelper<>(List.of(), 5).pageIndex(0)).isEqualTo(-1);
        assertThat(new PaginationHelper<>(List.of('a'), 1).pageIndex(1)).isEqualTo(-1);
    }

    @Test
    void shouldProvidePageIndexForNegativeItemIndices() throws Exception {
        assertThat(new PaginationHelper<>(List.of(0), 1).pageIndex(-1)).isEqualTo(-1);
        assertThat(new PaginationHelper<>(List.of(0), 1).pageIndex(-2)).isEqualTo(-1);
        assertThat(new PaginationHelper<>(List.of(0), 2).pageIndex(-1)).isEqualTo(-1);
        assertThat(new PaginationHelper<>(List.of(0, 1), 1).pageIndex(-1)).isEqualTo(-1);
        assertThat(new PaginationHelper<>(List.of(0, 1), 2).pageIndex(-1)).isEqualTo(-1);
        assertThat(new PaginationHelper<>(List.of(0, 1, 2, 3, 4), 2).pageIndex(-1)).isEqualTo(-1);
        assertThat(new PaginationHelper<>(List.of(), 1).pageIndex(-1)).isEqualTo(-1);
        assertThat(new PaginationHelper<>(List.of(), 1).pageIndex(2)).isEqualTo(-1);
        assertThat(new PaginationHelper<>(List.of(), 1).pageIndex(3)).isEqualTo(-1);
        assertThat(new PaginationHelper<>(List.of(), 1).pageIndex(4)).isEqualTo(-1);
    }
}
