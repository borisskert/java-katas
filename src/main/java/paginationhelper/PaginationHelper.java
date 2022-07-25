package paginationhelper;

import java.util.List;

/**
 * <a href="https://www.codewars.com/kata/515bb423de843ea99400000a/train/java">PaginationHelper</a>
 *
 * @param <I> the collection item type
 */
public class PaginationHelper<I> {

    private final List<I> collection;
    private final int itemsPerPage;

    /**
     * The constructor takes in an array of items and a integer indicating how many
     * items fit within a single page
     */
    public PaginationHelper(List<I> collection, int itemsPerPage) {
        this.collection = collection;
        this.itemsPerPage = itemsPerPage;
    }

    /**
     * returns the number of items within the entire collection
     */
    public int itemCount() {
        return collection.size();
    }

    /**
     * returns the number of pages
     */
    public int pageCount() {
        int itemCount = itemCount();
        int pages = itemCount / itemsPerPage;

        if (itemCount % itemsPerPage == 0) {
            return pages;
        }

        return pages + 1;
    }

    /**
     * returns the number of items on the current page. page_index is zero based.
     * this method should return -1 for pageIndex values that are out of range
     */
    public int pageItemCount(int pageIndex) {
        int pageCount = pageCount();

        if (pageIndex < 0 || pageIndex >= pageCount) {
            return -1;
        }

        int rest = itemCount() % itemsPerPage;

        if (rest == 0) {
            return itemsPerPage;
        }

        if (pageIndex == pageCount - 1) {
            return rest;
        }

        return itemsPerPage;
    }

    /**
     * determines what page an item is on. Zero based indexes
     * this method should return -1 for itemIndex values that are out of range
     */
    public int pageIndex(int itemIndex) {
        if (itemIndex < 0 || itemIndex >= itemCount()) {
            return -1;
        }

        return itemIndex / itemsPerPage;
    }
}
