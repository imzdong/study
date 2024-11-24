package org.imzdong.geektime.ebook;

import java.io.IOException;

public class FolderNotFoundError extends IOException {
    public FolderNotFoundError(String message) {
        super(message);
    }
}