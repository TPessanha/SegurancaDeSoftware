package Resources;

import java.util.List;

public class Capability {
    String identifier;
    List<AccessOperation> operations;

    public Capability(String identifier, List<AccessOperation> operations) {
        this.identifier = identifier;
        this.operations = operations;
    }
}
