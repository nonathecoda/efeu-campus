java ^
-jar C:/Users/hess/Documents/dev_swagger/openapi-generator.jar generate ^
-g java ^
-o ./out/ ^
-i C:/Users/hess/Documents/efeu/GitLab/box-scheduling-service/openapi/swagger.yaml ^
-c ./config.json


xcopy  ".\out\src\main\java\de\fzi\efeu" "..\..\src\main\java\de\fzi\efeu\" /K /D /H /Y /E
xcopy ".\out\src\test\java\de\fzi\efeu" "..\..\src\test\java\de\fzi\efeu\" /K /D /H /Y /E
xcopy ".\out\docs" "..\..\docs\" /K /D /H /Y /E