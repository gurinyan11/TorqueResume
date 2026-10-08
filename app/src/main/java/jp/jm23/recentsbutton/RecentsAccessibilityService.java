package jp.jm23.recentsbutton;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

public class RecentsAccessibilityService extends AccessibilityService {

    private static RecentsAccessibilityService instance;

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        instance = this;
    }

    @Override
    public void onDestroy() {
        if (instance == this) {
            instance = null;
        }
        super.onDestroy();
    }

    public static boolean showRecents() {
        if (instance == null) {
            return false;
        }

        return instance.performGlobalAction(
                AccessibilityService.GLOBAL_ACTION_RECENTS
        );
    }

    public static boolean clickTorque() {
        if (instance == null) {
            return false;
        }

        AccessibilityNodeInfo root = instance.getRootInActiveWindow();
        if (root == null) {
            return false;
        }

        return findAndClickTorque(root);
    }

    private static boolean findAndClickTorque(AccessibilityNodeInfo node) {
        if (node == null) {
            return false;
        }

        CharSequence text = node.getText();
        CharSequence desc = node.getContentDescription();

        if (containsTorque(text) || containsTorque(desc)) {

            AccessibilityNodeInfo target = node;

            while (target != null) {
                if (target.isClickable()) {
                    return target.performAction(
                            AccessibilityNodeInfo.ACTION_CLICK
                    );
                }
                target = target.getParent();
            }
        }

        for (int i = 0; i < node.getChildCount(); i++) {
            if (findAndClickTorque(node.getChild(i))) {
                return true;
            }
        }

        return false;
    }

    private static boolean containsTorque(CharSequence value) {
        if (value == null) {
            return false;
        }

        String s = value.toString().toLowerCase();

        return s.equals("torque")
                || s.contains("torque pro");
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        // 意図的に何もしない
    }

    @Override
    public void onInterrupt() {
    }
}
