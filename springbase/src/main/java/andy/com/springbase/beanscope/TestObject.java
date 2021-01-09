package andy.com.springbase.beanscope;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ThreadScope.name)
public class TestObject {
}
