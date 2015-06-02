/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.view.table;

import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QLineEdit;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QSizePolicy;
import com.trolltech.qt.gui.QSpacerItem;
import pl.lss.cjambi.ccms.resources.I18n;
import pl.lss.cjambi.ccms.resources.IconResources;
import pl.lss.cjambi.ccms.view.HBoxWidget;

/**
 *
 * @author ctran
 */
public abstract class Pager extends HBoxWidget {

    private QPushButton firstBtn, prevBtn, nextBtn, lastBtn, refreshBtn;
    protected QLineEdit pageNumLineEdit;
    protected long pageNum;
    protected long pageSize;

    public Pager(long pageSize) {
        super();
        pageNum = 1;
        this.pageSize = pageSize;

        firstBtn = new QPushButton(I18n.firstPage);
        firstBtn.setIcon(IconResources.FIRST_BUTTON_ICON);
        firstBtn.setIconSize(IconResources.ICON_16);
        firstBtn.clicked.connect(this, "onFirstBtnClicked()");
        addWidget(firstBtn, 0, Qt.AlignmentFlag.AlignLeft);

        prevBtn = new QPushButton(I18n.prevPage);
        prevBtn.setIcon(IconResources.PREV_BUTTON_ICON);
        prevBtn.setIconSize(IconResources.ICON_16);
        prevBtn.clicked.connect(this, "onPrevBtnClicked()");
        addWidget(prevBtn, 0, Qt.AlignmentFlag.AlignLeft);

        pageNumLineEdit = new QLineEdit(String.valueOf(pageNum));
        pageNumLineEdit.setFixedWidth(60);
        pageNumLineEdit.setAlignment(Qt.AlignmentFlag.AlignCenter);
        addWidget(pageNumLineEdit, 0, Qt.AlignmentFlag.AlignLeft);

        nextBtn = new QPushButton(I18n.nextPage);
        nextBtn.setIcon(IconResources.NEXT_BUTTON_ICON);
        nextBtn.setIconSize(IconResources.ICON_16);
        nextBtn.clicked.connect(this, "onNextBtnClicked()");
        addWidget(nextBtn, 0, Qt.AlignmentFlag.AlignLeft);

        lastBtn = new QPushButton(I18n.lastPage);
        lastBtn.setIcon(IconResources.LAST_BUTTON_ICON);
        lastBtn.setIconSize(IconResources.ICON_16);
        lastBtn.clicked.connect(this, "onLastBtnCLicked()");
        addWidget(lastBtn, 0, Qt.AlignmentFlag.AlignLeft);

        refreshBtn = new QPushButton(I18n.reload);
        refreshBtn.setIcon(IconResources.REFRESH_BUTTON_ICON);
        refreshBtn.setIconSize(IconResources.ICON_16);
        refreshBtn.clicked.connect(this, "onRefreshBtnCLicked()");
        addWidget(refreshBtn, 0, Qt.AlignmentFlag.AlignLeft);
        addSpacerItem(new QSpacerItem(0, 0, QSizePolicy.Policy.Expanding));
    }

    private void onFirstBtnClicked() {
        onPageNumChanged(1);
    }

    private void onPrevBtnClicked() {
        long prevPage = (pageNum - 1 > 0 ? pageNum - 1 : 1);
        onPageNumChanged(prevPage);
    }

    private void onNextBtnClicked() {
        long nextPage = pageNum + 1;
        onPageNumChanged(nextPage);
    }

    private void onLastBtnCLicked() {
        onPageNumChanged(getMaxPage());
    }

    public void onRefreshBtnCLicked() {
        try {
            pageNum = Integer.parseInt(pageNumLineEdit.text());
            long maxPage = getMaxPage();
            if (pageNum > maxPage) {
                pageNum = maxPage;
            }
            if (pageNum < 1) {
                pageNum = 1;
            }
            onPageNumChanged(pageNum);
        } catch (NumberFormatException ex) {
            //TO-DO
        }
    }

    protected void onPageNumChanged(long pageNum) {
        this.pageNum = pageNum;
        pageNumLineEdit.setText(String.valueOf(pageNum));
        fetchData();
    }

    protected abstract long getMaxPage();

    protected abstract void fetchData();

}
