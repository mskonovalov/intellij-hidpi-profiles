package ms.konovalov.intellij.hidpi;

import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("unused")
public class FontSizeForm {

    private FontSizeComponent component;
    private JPanel panel;
    private JList<FontProfile> profilesList;
    private JButton addCurrentButton;
    private JButton deleteButton;
    private java.util.List<FontProfile> profiles;

    FontSizeForm(FontSizeComponent component) {
        profiles = FontSizeComponent.getProfiles();
        this.component = component;
        profilesList.setModel(setModel());
        profilesList.setSelectedIndex(0);
        profilesList.setCellRenderer(new DefaultListCellRenderer() {
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                FontProfile fp = (FontProfile) value;
                label.setText(fp.getName());
                return label;
            }
        });

        deleteButton.addActionListener(e -> {
            int idx = profilesList.getSelectedIndex();
            if (idx != -1) {
                ((DefaultListModel) profilesList.getModel()).remove(idx);
                setModified(true);
                if (profilesList.getModel().getSize() == 0) {
                    return;
                }
                if (idx > profilesList.getModel().getSize() - 1) {
                    profilesList.setSelectedIndex(idx - 1);
                } else {
                    profilesList.setSelectedIndex(idx);
                }
            }
        });

        addCurrentButton.addActionListener(e -> {
            String profileName = Messages.showInputDialog("Provide name for new Profile", "HDPI Profile ", null);
            if (profileName == null) {
                return;
            }
            FontProfile activeProfile = FontProfileManager.readCurrentProfile(profileName, false);
            ((DefaultListModel<FontProfile>) profilesList.getModel()).addElement(activeProfile);
            profilesList.setSelectedIndex(profilesList.getModel().getSize() - 1);
            setModified(true);
        });
    }

    @NotNull
    private DefaultListModel<FontProfile> setModel() {
        DefaultListModel<FontProfile> model = new DefaultListModel<>();
        for (FontProfile fp : profiles) {
            model.addElement(fp);
        }
        return model;
    }

    void reset() {
        profilesList.setModel(setModel());
        setModified(false);
    }

    void apply() {
        profiles.clear();
        ListModel<FontProfile> model = profilesList.getModel();
        for (int i = 0; i < model.getSize(); i++) {
            profiles.add(model.getElementAt(i));
        }
        setModified(false);
    }

    JPanel getPanel() {
        return panel;
    }


    private void setModified(boolean modified) {
        component.setModified(modified);
    }

}

