package ms.konovalov.intellij.hidpi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FontProfile {

    private boolean active;
    private String name;
    private boolean overrideGlobalFont;
    private int globalFontSize;
    private int editorFontSize;
    private int consoleFontSize;
}
