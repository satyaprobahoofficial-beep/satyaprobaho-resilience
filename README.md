# Satyaprobaho & Nexora Manager

Android companion workspace for two separate organizations:
- **Satyaprobaho** — newsroom/admin entry and public website.
- **Nexora International Technology** — website, solutions and client portal entry.

## Design
Uses a premium dark enterprise interface inspired by the widely adopted Google Material Design 3 design language: deep navy surfaces, electric blue/violet/cyan/emerald/amber accents, rounded cards, gradient actions and dark dialogs. No white page backgrounds or white cards/buttons are used in the app UI.

## Available in this build
- Separate Satyaprobaho and Nexora dashboard entry points.
- Satyaprobaho admin and public website links.
- Nexora website, solutions and client portal links.
- Configurable HTTPS Nexora admin URL (the default is the public website and is not represented as a verified admin endpoint).
- Integration status page with honest not-connected labels and provider dashboard shortcuts.
- Local manual API usage/cost ledger.
- Security notes and no embedded passwords/API secrets.
- Secure HTTPS-only WebView navigation.

## Not yet connected
Live Cloudflare/Supabase/GitHub/Vercel/YouTube/Meta/Brevo API telemetry, server-side multi-user roles, encrypted secret vault, cloud backup, audit logs, automated billing and remote feature management require authorized backend/API connections. This app does not fabricate live usage or connection statuses.

## Build
GitHub Actions builds a debug APK on push or manual workflow dispatch. Open **Actions → Build Android APK → latest successful run → Artifacts** and download `Satyaprobaho-Nexora-Manager-debug`.
