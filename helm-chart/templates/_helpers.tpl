{{- define "shopnow-order-management.name" -}}
{{- .Chart.Name -}}
{{- end -}}

{{- define "shopnow-order-management.fullname" -}}
{{- .Release.Name }}-{{ .Chart.Name }}
{{- end -}}
