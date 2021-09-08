import com.github.luomingxu.idea.entity.HttpMethod;
import com.intellij.navigation.ItemPresentation;
import com.intellij.navigation.NavigationItem;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import org.jetbrains.annotations.Nullable;

public class MyNavigationItem implements NavigationItem {
    private PsiElement psielment;
    private NavigationItem navigationItem;
    private String name;
    private HttpMethod httpMethod;
    private FilterDemo demo;

    public MyNavigationItem(String name, HttpMethod httpMethod, PsiElement psiElement) {
        this.psielment = psiElement;
        if (psiElement instanceof PsiMethod) {
            PsiMethod method = (PsiMethod) psiElement;
            this.name = method.getName();
        }
        if (psiElement instanceof NavigationItem) {
            this.navigationItem = (NavigationItem) psiElement;
        }
        this.name = name;
        this.httpMethod = httpMethod;
        demo = new FilterDemo(name, name);
    }

    @Nullable
    @Override
    public String getName() {
        return name;
    }

    @Nullable
    @Override
    public ItemPresentation getPresentation() {
        return new MyItemPresentation(name, httpMethod);
    }

    @Override
    public void navigate(boolean b) {
        if (null != navigationItem) {
            navigationItem.navigate(b);
        }
    }

    @Override
    public boolean canNavigate() {
        return true;
    }

    @Override
    public boolean canNavigateToSource() {
        return true;
    }

    public String getValue() {
        return this.name;
    }

    public FilterDemo getDemo() {
        return demo;
    }
}
