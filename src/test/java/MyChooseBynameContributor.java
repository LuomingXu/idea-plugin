import com.github.luomingxu.idea.util.GetAllApi;
import com.intellij.navigation.ChooseByNameContributor;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyChooseBynameContributor implements ChooseByNameContributor {
    public static List<MyNavigationItem> list = new ArrayList<>();

    public MyChooseBynameContributor(Project project) {

        for (int i = 0; i < 20; i++) {
            list.add(new MyNavigationItem(i + "", null, null));
        }
    }

    /**
     * @return java.lang.String[]
     * @description: 提供全部选项
     * @author: chenzhiwei
     * @create: 2020/5/16 22:31
     */
    @Override
    public String @NotNull [] getNames(Project project, boolean b) {
        return list.stream().map(MyNavigationItem::getValue).toArray(String[]::new);
    }

    /**
     * @return com.intellij.navigation.NavigationItem[]
     * @description: 匹配到符合的项
     * @author: chenzhiwei
     * @create: 2020/5/16 22:31
     */
    @Override
    public NavigationItem @NotNull [] getItemsByName(
            String s, String s1, Project project, boolean b) {

        return list.stream().filter(p -> p.getValue().equals(s)).toArray(NavigationItem[]::new);
    }
}
