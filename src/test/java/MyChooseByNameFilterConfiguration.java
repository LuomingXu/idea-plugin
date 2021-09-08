import com.intellij.ide.util.gotoByName.ChooseByNameFilterConfiguration;

public class MyChooseByNameFilterConfiguration extends ChooseByNameFilterConfiguration<FilterDemo> {


    @Override
    protected String nameForElement(FilterDemo filterDemo) {
        return filterDemo.getValue();
    }
}