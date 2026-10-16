# Running the OpenTV project

How this repository (`suatsalih/opentv`) is set up, and how to publish a new version. The
one-time setup is already done; this is the reference for when you need it again.

---

## What is already set up

| Setting | Where | State |
|---|---|---|
| GitHub Actions | Settings → Actions → General | Allowed |
| Signing key | Settings → Secrets and variables → Actions | `KEYSTORE_BASE64`, `KEYSTORE_PASSWORD`, `KEY_ALIAS`, `KEY_PASSWORD` |
| Website (Play listing) | Settings → Pages → Source: **GitHub Actions** | https://suatsalih.github.io/opentv/ |
| Donations | `.github/FUNDING.yml` | PayPal: suat.salih@gmail.com |

Keep `upload.jks` and its password somewhere safe outside the repo (a password manager). If
the key is lost, no update can ever be installed over an existing copy of the app.

## Publishing a new version

OpenTV is distributed through Google Play only. GitHub carries the source code and release notes,
never an APK.

1. Bump `versionCode` (must go up every release) and `versionName` in `app/build.gradle.kts`,
   and add a `## <version>` section at the top of `CHANGELOG.md` — its text becomes the
   release notes.
2. Merge that to `main`.
3. Go to **Releases → Draft a new release**, create the tag `v<version>` on `main`, and publish.
   Do not attach any files.

Publishing the release starts the **Release** workflow: it runs the tests, builds the Play app
bundle signed with the project key, keeps it as a workflow artifact for 14 days, and writes the
release notes. GitHub adds the source-code archives to the release itself. If it ever does not
start, open **Actions → Release → Run workflow** and choose the tag.

4. Download the `opentv-play-v<version>` artifact from that run and upload the `.aab` in Play
   Console (see [docs/PLAY_STORE.md](docs/PLAY_STORE.md#14-each-new-version)).

## Before telling people about a release

Install it from Play on a real device first — an Android TV box and a phone if you can — and
use it for a few days with a real provider. A first impression is only made once.
