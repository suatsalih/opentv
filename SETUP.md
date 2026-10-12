# Running the OpenTV project

How this repository (`suatsalih/opentv`) is set up, and how to publish a new version. The
one-time setup is already done; this is the reference for when you need it again.

---

## What is already set up

| Setting | Where | State |
|---|---|---|
| GitHub Actions | Settings → Actions → General | Allowed |
| Signing key | Settings → Secrets and variables → Actions | `KEYSTORE_BASE64`, `KEYSTORE_PASSWORD`, `KEY_ALIAS`, `KEY_PASSWORD` |
| Install page | Settings → Pages → Source: **GitHub Actions** | https://suatsalih.github.io/opentv/ |
| Donations | `.github/FUNDING.yml`, README, About screen | PayPal: suat.salih@gmail.com |

Keep `upload.jks` and its password somewhere safe outside the repo (a password manager). If
the key is lost, no update can ever be installed over an existing copy of the app.

## Publishing a new version

1. Bump `versionCode` (must go up every release) and `versionName` in `app/build.gradle.kts`,
   and add a `## <version>` section at the top of `CHANGELOG.md` — its text becomes the
   release notes.
2. Merge that to `main`.
3. Go to **Releases → Draft a new release**, create the tag `v<version>` on `main`, and publish.

Publishing the release starts the **Release** workflow: it runs the tests, builds the APK
signed with the project key, and attaches it to the release. The install page and the in-app
updater pick it up on their own. If it ever does not start, open **Actions → Release → Run
workflow** and choose the tag.

## Installing on a TV

Open the install page and follow the steps there. The address to type into the **Downloader**
app is:

```
suatsalih.github.io/opentv/apk
```

It always redirects to the newest APK, so it never changes between releases.

## Before telling people about a release

Install it on a real device first — an Android TV box, a Fire TV stick and a phone if you can —
and use it for a few days with a real provider. A first impression is only made once.
