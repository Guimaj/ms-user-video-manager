data "aws_ecr_repository" "repository" {
  name = "${var.project_name}-repository"
}


resource "kubernetes_deployment" "deployment" {
  metadata {
    name = "${var.project_name}-deployment"
  }
  spec {
    replicas = 1
    selector {
      match_labels = {
        app = var.project_name
      }
    }
    template {
      metadata {
        labels = {
          app = var.project_name
        }
      }
      spec {
        container {
          image = "${data.aws_ecr_repository.repository.repository_url}:${var.appversion}"
          name  = var.project_name
          port {
            container_port = 8080
          }
          resources {
            limits = {
              cpu    = "2"
              memory = "4096Mi"
            }
            requests = {
              cpu    = "1"
              memory = "2048Mi"
            }
          }

          env {
            name  = "AWS_REGION"
            value = var.region
          }

          env {
            name  = "S3_BUCKET_NAME"
            value = var.bucket
          }

          env {
            name  = "AWS_ACCESS_KEY_ID"
            value = var.accesskey
          }

          env {
            name = "AWS_ACCESS_SECRET_KEY"
            value = var.accessSecretKey
          }

          env {
            name = "COGNITO_JWK_URI"
            value = var.cognitoJwkUri
          }

          env {
            name = "MONGODB_URL"
            value = var.mongodbUrl
          }

          env {
            name = "COGNITO_USER_POOL"
            value = var.cognitoUserPool
          }

          env {
            name = "COGNITO_CLIENT_ID"
            value = var.cognitoClientId
          }

          env {
            name = "COGNITO_REGION"
            value = var.region
          }

          env {
            name = "COGNITO_SECRET"
            value = var.cognitoSecret
          }

          # liveness_probe {
          #   http_get {
          #     path = "/"
          #     port = 8080
          #   }

          #   initial_delay_seconds = 3
          #   period_seconds        = 3
          # }
        }
      }
    }
  }
}