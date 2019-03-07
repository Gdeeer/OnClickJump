package com.gdeer.plugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.*;
import com.intellij.openapi.project.Project;

public class JumpAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // 获取编辑器model
        final Editor mEditor = e.getData(PlatformDataKeys.EDITOR);

        final Project project = e.getProject();
        if (null == mEditor || project == null) {
            return;
        }

        // 获取代码内容
        final Document document = mEditor.getDocument();
        // 获取光标model
        final CaretModel caretModel = mEditor.getCaretModel();
        // 获取选中区域model
        final SelectionModel selectionModel = mEditor.getSelectionModel();
        // 获取滚动model
        final ScrollingModel scrollingModel = mEditor.getScrollingModel();

        selectionModel.selectWordAtCaret(false);
        String selectedText = selectionModel.getSelectedText();
        selectionModel.removeSelection();

        if (selectedText != null && selectedText.equals("setOnClickListener")) {
            String documentText = document.getText();
            String documentLines[] = documentText.split("\n");
            for (int i = 0; i < documentLines.length; i++) {
                String lineText = documentLines[i];
                if (lineText.indexOf("public void onClick(View ") == 4) {
                    int onClickLine = i;
                    int onClickColumn = lineText.indexOf("onClick");
                    LogicalPosition onClickLogicalPosition = new LogicalPosition(onClickLine, onClickColumn);
                    scrollingModel.scrollTo(onClickLogicalPosition, ScrollType.MAKE_VISIBLE);
                    caretModel.moveToLogicalPosition(onClickLogicalPosition);
                    break;
                }
            }
        }
    }
}
