import com.intellij.ide.actions.GotoActionBase;
import com.intellij.ide.util.gotoByName.ChooseByNameFilter;
import com.intellij.ide.util.gotoByName.ChooseByNamePopup;
import com.intellij.navigation.ChooseByNameContributor;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NewGotoActionBase extends GotoActionBase {

    @Override
    protected void gotoActionPerformed(@NotNull AnActionEvent e) {
        MyChooseBynameContributor chooseByname = new MyChooseBynameContributor(e.getProject());
        ChooseByNameContributor[] butor = new ChooseByNameContributor[]{chooseByname};
        FilterModel model = new FilterModel(e.getProject(), butor);
        // 初始化回调函数
        GotoActionCallback<FilterDemo> callback = new GotoActionCallback<>() {
            @Override
            protected ChooseByNameFilter<FilterDemo> createFilter(@NotNull ChooseByNamePopup popup) {
                return new MyChooseByNameFilter(popup, model, e.getProject());
            }

            @Override
            public void elementChosen(ChooseByNamePopup chooseByNamePopup, Object o) {
                // 选择之后的操作
                //Messages.showInfoMessage("Hello", "Title");


                if (o instanceof MyNavigationItem) {
                    MyNavigationItem navigationItem = (MyNavigationItem) o;
                    if (navigationItem.canNavigate()) {
                        navigationItem.navigate(true);
                    }
                }
            }
        };
        showNavigationPopup(e, model, callback);
    }
}