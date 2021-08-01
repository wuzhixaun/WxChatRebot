def label = "slave-${UUID.randomUUID().toString()}"

// 引用共享库
@Library("jenkins_shareLibrary")

// 应用共享库中的方法
def tools = new org.devops.tools()
def sonarapi = new org.devops.sonarAPI()
def sendEmail = new org.devops.sendEmail()
def build = new org.devops.build()
def sonar = new org.devops.sonarqube()

podTemplate(label: label, containers: [
  containerTemplate(name: 'jdk-maven', image: 'appinair/jdk11-maven:latest', command: 'cat', ttyEnabled: true),
  containerTemplate(name: 'docker', image: 'docker:latest', command: 'cat', ttyEnabled: true),
  containerTemplate(name: 'sonar-scanner', image: 'registry.cn-hangzhou.aliyuncs.com/rookieops/sonar-scanner:latest', command: 'cat', ttyEnabled: true),
  containerTemplate(name: 'kubectl', image: 'cnych/kubectl', command: 'cat', ttyEnabled: true)
], serviceAccount: 'jenkins-admin', volumes: [
  hostPathVolume(mountPath: '/home/jenkins/.kube', hostPath: '/root/.kube'),
  hostPathVolume(mountPath: '/var/run/docker.sock', hostPath: '/var/run/docker.sock')
]) {
  node(label) {
    def repo = checkout scm
    def gitCommit = repo.GIT_COMMIT
    def gitBranch = repo.GIT_BRANCH
    
    // 获取 git commit id 作为镜像标签
    def imageTag = sh(script: "git rev-parse --short HEAD", returnStdout: true).trim()
    // 仓库地址
    def registryUrl = "registry-vpc.cn-shenzhen.aliyuncs.com"
    def imageEndpoint = "wuzhixuan/WxChatRebot"
    // 镜像
    def image = "${registryUrl}/${imageEndpoint}:${imageTag}"
    
    stage('单元测试') {
      echo "测试阶段"
    }
    
    stage('代码编译打包') {
      container('jdk-maven') {
        script{
            tools.PrintMes("编译打包","blue")
            build.DockerBuild("${buildShell}")
        }
      }
    }
    
    // 代码扫描
            stage('CodeScanner') {
                steps {
                    container('sonar-scanner') {
                        script {
                            tools.PrintMes("代码扫描","green")
                            tools.PrintMes("搜索项目","green")
                            result = sonarapi.SearchProject("${JOB_NAME}")
                            println(result)
    
                            if (result == "false"){
                                println("${JOB_NAME}---项目不存在,准备创建项目---> ${JOB_NAME}！")
                                sonarapi.CreateProject("${JOB_NAME}")
                            } else {
                                println("${JOB_NAME}---项目已存在！")
                            }
    
                            tools.PrintMes("代码扫描","green")
                            sonar.SonarScan("${JOB_NAME}","${JOB_NAME}","src")
    
                            sleep 10
                            tools.PrintMes("获取扫描结果","green")
                            result = sonarapi.GetProjectStatus("${JOB_NAME}")
    
                            println(result)
                            if (result.toString() == "ERROR"){
                                toemail.Email("代码质量阈错误！请及时修复！",userEmail)
                                error " 代码质量阈错误！请及时修复！"
    
                            } else {
                                println(result)
                            }
                        }
                    }
                }
            }
    
  }
}
