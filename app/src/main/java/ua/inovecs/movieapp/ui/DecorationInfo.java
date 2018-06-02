package ua.inovecs.movieapp.ui;

class DecorationInfo {
    private int titleResourceId;
    private boolean showBackArrow;
    private boolean shouldDecorate;

    public int getTitleResourceId() {
        return titleResourceId;
    }

    public void setTitleResourceId(int titleResourceId) {
        this.titleResourceId = titleResourceId;
    }

    public boolean isShowBackArrow() {
        return showBackArrow;
    }

    public void setShowBackArrow(boolean showBackArrow) {
        this.showBackArrow = showBackArrow;
    }


    public boolean isShouldDecorate() {
        return shouldDecorate;
    }

    public void setShouldDecorate(boolean shouldDecorate) {
        this.shouldDecorate = shouldDecorate;
    }
}
