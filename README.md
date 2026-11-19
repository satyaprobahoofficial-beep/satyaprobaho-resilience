# Satyaprobaho & Nexora Manager

A small Android WebView companion app with separate entry screens for:
- Satyaprobaho admin: https://satyaprobaho.satyaprobaho.workers.dev/admin.html
- Nexora: configurable HTTPS admin URL, defaulting to the verified public site.

Default login email labels:
- Satyaprobaho: satyaprobaho.official@gmail.com
- Nexora: computer26training@gmail.com

No passwords, access tokens, or API secrets are bundled. Provider quota and billing integrations are not implemented until their APIs and authorization are connected.

## Build
GitHub Actions builds a debug APK on push or manual workflow dispatch. Download the APK from Actions > Build Android APK > Artifacts.
