package com.yuyun.design.factory;

/**
 * ------------------- 按钮 -----------------------------------
 * 按钮接口：抽象产品
 */
interface Button {
    /**
     * 陈列
     */
    public void display();
}

/**
 * ------------------- 文本框 -------------------------------
 * 文本框接口：抽象产品
 */
interface TextField {
    /**
     * 陈列
     */
    public void display();
}

/**
 * ------------------- 组合框 -------------------------------
 * 组合框接口：抽象产品
 */
interface ComboBox {
    /**
     * 陈列
     */
    public void display();
}

/**
 * ------------------- 界面皮肤 -----------------------------
 * 界面皮肤工厂接口：抽象工厂
 */
interface SkinFactory {
    /**
     * “创建”按钮
     *
     * @return {@link Button}
     */
    public Button createButton();

    /**
     * 创建文本字段
     *
     * @return {@link TextField}
     */
    public TextField createTextField();

    /**
     * 创建组合框
     *
     * @return {@link ComboBox}
     */
    public ComboBox createComboBox();
}

/**
 * 抽象工厂模式
 *
 * @author hyh
 * @since 2024-02-26
 */
public class AbstractClient {
    public static void main(String[] args) {
        SummerSkinFactory summerSkinFactory = new SummerSkinFactory();
        Button button = summerSkinFactory.createButton();
        ComboBox comboBox = summerSkinFactory.createComboBox();
        TextField textField = summerSkinFactory.createTextField();

        button.display();
        comboBox.display();
        textField.display();
    }
}

/**
 * Spring按钮类：具体产品
 */
class SpringButton implements Button {
    public void display() {
        System.out.println("显示浅绿色按钮。");
    }
}

/**
 * Summer按钮类：具体产品
 */
class SummerButton implements Button {
    public void display() {
        System.out.println("显示浅蓝色按钮。");
    }
}

/**
 * Spring文本框类：具体产品
 */
class SpringTextField implements TextField {
    public void display() {
        System.out.println("显示绿色边框文本框。");
    }
}

/**
 * Summer文本框类：具体产品
 */
class SummerTextField implements TextField {
    public void display() {
        System.out.println("显示蓝色边框文本框。");
    }
}

/**
 * Spring组合框类：具体产品
 */
class SpringComboBox implements ComboBox {
    public void display() {
        System.out.println("显示绿色边框组合框。");
    }
}

/**
 * Summer组合框类：具体产品
 */
class SummerComboBox implements ComboBox {
    public void display() {
        System.out.println("显示蓝色边框组合框。");
    }
}

/**
 * Spring皮肤工厂：具体工厂
 */
class SpringSkinFactory implements SkinFactory {
    public Button createButton() {
        return new SpringButton();
    }

    public TextField createTextField() {
        return new SpringTextField();
    }

    public ComboBox createComboBox() {
        return new SpringComboBox();
    }
}

/**
 * Summer皮肤工厂：具体工厂
 */
class SummerSkinFactory implements SkinFactory {
    public Button createButton() {
        return new SummerButton();
    }

    public TextField createTextField() {
        return new SummerTextField();
    }

    public ComboBox createComboBox() {
        return new SummerComboBox();
    }
}