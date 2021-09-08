import com.github.luomingxu.idea.entity.HttpMethod;
import com.github.luomingxu.idea.util.RESTFullIcons;
import com.intellij.icons.AllIcons;
import com.intellij.navigation.ItemPresentation;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class MyItemPresentation implements ItemPresentation {
    private String name;
    private HttpMethod httpMethod;

    public MyItemPresentation(String name, HttpMethod httpMethod) {
        this.name = name;
        this.httpMethod = httpMethod;
    }

    /**
     * @return java.lang.String
     * @description: 搜索结果最终显示
     * @author: chenzhiwei
     * @create: 2020/5/17 10:16
     */
    @Nullable
    @Override
    public String getPresentableText() {
        return name;
    }

    /**
     * @return java.lang.String
     * @description: 搜索结果的辅助说明
     * @author: chenzhiwei
     * @create: 2020/5/17 10:16
     */
    @Nullable
    @Override
    public String getLocationString() {
        return "getLocationString";
    }

    /**
     * @return javax.swing.Icon
     * @description: 搜索结果的图标
     * @author: chenzhiwei
     * @create: 2020/5/17 10:16
     */
    @Nullable
    @Override
    public Icon getIcon(boolean b) {

        return RESTFullIcons.INSTANCE.getIcon(httpMethod);
    }
}