def label = "slave-${UUID.randomUUID().toString()}"

podTemplate(label: label, containers: [
  containerTemplate(name: 'jdk-maven', image: 'appinair/jdk11-maven:latest', command: 'cat', ttyEnabled: true),
  containerTemplate(name: 'docker', image: 'docker:latest', command: 'cat', ttyEnabled: true),
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
    def imageEndpoint = "wuzhixuan/wx-chat-rebot"
    // 镜像
    def image = "${registryUrl}/${imageEndpoint}:${imageTag}"
    
    stage('单元测试') {
      echo "测试阶段"
    }
    
    stage('代码编译打包') {
      container('jdk-maven') {
        echo "代码编译打包阶段"
        sh "mvn clean package -Dmaven.test.skip=true"
      }
    }
    
    stage('构建 Docker 镜像') {
      withCredentials([usernamePassword(credentialsId: 'dock-auth-ali', passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_USER')]) {
          container('docker') {
            echo "3. 构建 Docker 镜像阶段"
            sh """
              docker login ${registryUrl} -u ${DOCKER_USER} -p ${DOCKER_PASSWORD}
              docker build -t ${image} .
              docker push ${image}
              """
          }
      }
    }
    
    stage('运行 Kubectl') {
      container('kubectl') {
        echo "查看 K8S 集群 Pod 列表"
        sh """
          sed -i 's#\$image#${image}#' deployment.yaml
          """
        kubernetesDeploy(enableConfigSubstitution: false, kubeconfigId: 'kubeconfig1', configs: 'deployment.yaml')
      }
    }
  }
}
