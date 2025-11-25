# Shape View Wrapper

Thay thế việc tạo drawable XML truyền thống bằng các thuộc tính view trực tiếp.

## Cài đặt
```gradle
def version = "1.0.1"
implementation "com.github.khongphaiduycuannn:shape-view-wrapper:$version"
```

## Tổng quan

Shape View Wrapper cung cấp các custom view hỗ trợ styling trực tiếp thông qua XML attributes, loại bỏ nhu cầu tạo các file drawable resource riêng biệt.

## Các View được hỗ trợ

Thêm tiền tố `Shape` vào các view Android chuẩn:
- `ShapeView`
- `ShapeTextView`
- `ShapeButton`
- `ShapeImageView`
- `ShapeEditText`
- `ShapeConstraintLayout`
- `ShapeLinearLayout`
- `ShapeFrameLayout`
- `ShapeRelativeLayout`
- Và hầu hết các View/ViewGroup phổ biến khác

## Danh sách thuộc tính

### Bo góc & Hình dạng

| Thuộc tính | Kiểu | Mô tả |
|-----------|------|-------|
| `app:shape_radius` | dimension | Bo tròn tất cả 4 góc với cùng một giá trị |
| `app:shape_radiusInTopStart` | dimension | Bo tròn góc trên bên trái |
| `app:shape_radiusInTopEnd` | dimension | Bo tròn góc trên bên phải |
| `app:shape_radiusInBottomStart` | dimension | Bo tròn góc dưới bên trái |
| `app:shape_radiusInBottomEnd` | dimension | Bo tròn góc dưới bên phải |

### Màu nền đơn sắc

| Thuộc tính | Kiểu | Mô tả |
|-----------|------|-------|
| `app:shape_solidColor` | color | Màu nền đơn sắc |

### Gradient nền

| Thuộc tính | Kiểu | Mô tả |
|-----------|------|-------|
| `app:shape_solidGradientStartColor` | color | Màu bắt đầu của gradient nền |
| `app:shape_solidGradientCenterColor` | color | Màu trung tâm của gradient nền (tùy chọn) |
| `app:shape_solidGradientEndColor` | color | Màu kết thúc của gradient nền |
| `app:shape_solidGradientOrientation` | enum | Hướng của gradient: `startToEnd`, `endToStart`, `topToBottom`, `bottomToTop` |

### Viền (Border)

| Thuộc tính | Kiểu | Mô tả |
|-----------|------|-------|
| `app:shape_strokeSize` | dimension | Độ dày của viền |
| `app:shape_strokeColor` | color | Màu viền đơn sắc |
| `app:shape_strokeGradientStartColor` | color | Màu bắt đầu của gradient viền |
| `app:shape_strokeGradientCenterColor` | color | Màu trung tâm của gradient viền (tùy chọn) |
| `app:shape_strokeGradientEndColor` | color | Màu kết thúc của gradient viền |
| `app:shape_strokeGradientOrientation` | enum | Hướng của gradient viền |

### Đổ bóng

| Thuộc tính | Kiểu | Mô tả |
|-----------|------|-------|
| `app:shape_shadowSize` | dimension | Độ mờ/blur của bóng đổ |
| `app:shape_shadowColor` | color | Màu của bóng đổ |
| `app:shape_shadowOffsetX` | dimension | Độ lệch ngang của bóng (âm = trái, dương = phải) |
| `app:shape_shadowOffsetY` | dimension | Độ lệch dọc của bóng (âm = lên, dương = xuống) |

### Định dạng chữ

*Áp dụng đối với các View chứa text*

| Thuộc tính | Kiểu | Mô tả |
|-----------|------|-------|
| `app:shape_textColor` | color | Màu chữ đơn sắc |
| `app:shape_textStartColor` | color | Màu bắt đầu của gradient text |
| `app:shape_textCenterColor` | color | Màu trung tâm của gradient text (tùy chọn) |
| `app:shape_textEndColor` | color | Màu kết thúc của gradient text |
| `app:shape_textGradientOrientation` | enum | Hướng gradient text: `horizontal`, `vertical`, `diagonal` |
| `app:shape_textStrokeColor` | color | Màu viền chữ |
| `app:shape_textStrokeSize` | dimension | Độ dày viền chữ |

## Ví dụ hoàn chỉnh
Tham khảo trong `activity_main.xml`

## Sử dụng trong code

Đối với tất cả các ShapeView, chứa bên trong một inner background builder.

Đối với các View chứa text, có thêm một inner text builder.

Config các builder này để áp dụng hiệu ứng.

**Ví dụ với Background Builder:**
```kotlin
binding.shapeButton.shapeDrawableBuilder
    .setSolidColor(R.color.purple_200)
    .setRadius(15f)
    .intoBackground()
```

**Ví dụ với Text Builder:**
```kotlin
binding.shapeButton.textColorBuilder
    .setTextColor(R.color.purple_200)
    .intoTextColor()
```

**Lưu ý:**
- Các phương thức setter tương ứng với các attributes trong XML
- Phải gọi `intoBackground()` để apply config vào background của View
- Phải gọi `intoTextColor()` để apply config vào text của View