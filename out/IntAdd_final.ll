; ModuleID = 'llvm-link'
source_filename = "llvm-link"

@.str = private unnamed_addr constant [4 x i8] c"%d\0A\00", align 1

define void @main() {
  %"flat$$$0" = alloca i32
  %temp.1 = call i32 @_IntAdd_f(i32 0, i32 10)
  store i32 %temp.1, i32* %"flat$$$0"
  %temp.2 = load i32, i32* %"flat$$$0"
  call void @_IntAdd_print(i32 %temp.2)
  ret void
}

define i32 @_IntAdd_f(i32 %x, i32 %y) {
  %temp.0 = alloca i1, i1 true
  %"flat$$$1" = alloca i1, i1 true
  %arg_x = alloca i32
  store i32 %x, i32* %arg_x
  %arg_y = alloca i32
  store i32 %y, i32* %arg_y
  br label %label.3

label.3:                                          ; preds = %label.2, %0
  %temp.3 = load i32, i32* %arg_x
  %temp.4 = load i32, i32* %arg_y
  %temp.5 = icmp slt i32 %temp.3, %temp.4
  store i1 %temp.5, i1* %temp.0
  %temp.6 = load i1, i1* %temp.0
  br i1 %temp.6, label %label.5, label %label.4

label.5:                                          ; preds = %label.3
  %temp.7 = load i32, i32* %arg_x
  call void @_IntAdd_print(i32 %temp.7)
  %temp.8 = load i32, i32* %arg_y
  call void @_IntAdd_print(i32 %temp.8)
  %temp.9 = load i32, i32* %arg_x
  %temp.10 = add i32 %temp.9, 1
  store i32 %temp.10, i32* %arg_x
  %temp.11 = load i32, i32* %arg_y
  %temp.12 = sub i32 %temp.11, 1
  store i32 %temp.12, i32* %arg_y
  %temp.13 = load i32, i32* %arg_x
  %temp.14 = icmp slt i32 %temp.13, 0
  store i1 %temp.14, i1* %"flat$$$1"
  %temp.15 = load i1, i1* %"flat$$$1"
  br i1 %temp.15, label %label.0, label %label.1

label.0:                                          ; preds = %label.5
  ret i32 1
                                                  ; No predecessors!
  br label %label.2

label.1:                                          ; preds = %label.5
  br label %label.2

label.2:                                          ; preds = %label.1, %1
  br label %label.3

label.4:                                          ; preds = %label.3
  ret i32 0
}

define void @_IntAdd_print(i32 %x) {
  %call = call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([4 x i8], [4 x i8]* @.str, i32 0, i32 0), i32 %x)
  ret void
}

declare i32 @printf(i8*, ...)

define void @_IntComparisons_print(i32 %x) {
  %call = call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([4 x i8], [4 x i8]* @.str, i32 0, i32 0), i32 %x)
  ret void
}

define void @_Conditions_print(i32 %x) {
  %call = call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([4 x i8], [4 x i8]* @.str, i32 0, i32 0), i32 %x)
  ret void
}