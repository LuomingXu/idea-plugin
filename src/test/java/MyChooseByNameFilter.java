import com.intellij.ide.util.gotoByName.ChooseByNameFilter;
import com.intellij.ide.util.gotoByName.ChooseByNamePopup;
import com.intellij.ide.util.gotoByName.FilteringGotoByModel;

import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MyChooseByNameFilter extends ChooseByNameFilter<FilterDemo> {

    public MyChooseByNameFilter(@NotNull ChooseByNamePopup popup, @NotNull FilteringGotoByModel<FilterDemo> model, @NotNull Project project) {
        
        super(popup, model, new MyChooseByNameFilterConfiguration(), project);
    }

    @Override
    protected String textForFilterValue(@NotNull FilterDemo filterDemo) {
        return filterDemo.getValue();
    }

    @Nullable
    @Override
    protected Icon iconForFilterValue(@NotNull FilterDemo filterDemo) {
        return null;
    }

    @NotNull
    @Override
    protected Collection<FilterDemo> getAllFilterValues() {

        List<MyNavigationItem> list = MyChooseBynameContributor.list;
        List<FilterDemo> collect = list.parallelStream().map(MyNavigationItem::getDemo)
                .collect(Collectors.toList());
        return collect;
    }
}
