# Releasing

Releases are cut by publishing a GitHub release (see `SETUP.md`), or by pushing a tag:

```bash
git tag v0.11.9
git push origin v0.11.9
```

`.github/workflows/release.yml` runs the tests, assembles the release APK and attaches it to the
GitHub release.

## Signing

Release builds are signed with the project's own key, supplied to the workflow through the four
repository secrets below. The release notes say `signed: yes`.

If those secrets are ever missing, `app/build.gradle.kts` falls back to the Android debug key so
the build still produces an installable APK. Do not publish such a build: Android refuses to
install a debug-signed update over a properly signed copy, so users would have to uninstall and
lose their settings.

### Setting up signing (already done for this repository)

1. Generate a keystore:

   ```bash
   keytool -genkeypair -v -keystore upload.jks -keyalg RSA -keysize 4096 \
     -validity 10000 -alias opentv
   ```

2. Add these repository secrets under **Settings → Secrets and variables → Actions**:
   `KEYSTORE_BASE64` (the output of `base64 -i upload.jks`), `KEYSTORE_PASSWORD`, `KEY_ALIAS`,
   `KEY_PASSWORD`.

3. That is all — `release.yml` and `build.gradle.kts` already pick them up. The release notes
   will report `signed: yes` instead of `debug-key`.

**The keystore must belong to the project, not to one person.** A single maintainer holding
the only copy is the same single point of failure this project exists to avoid — if they go
quiet, nobody can ship an upgrade, and every user is stranded on whatever version they have.
Share it among at least two maintainers, and back it up somewhere that is not one laptop.

Losing the keystore is unrecoverable. There is no reset.

## Version numbers

Bump `versionCode` and `versionName` in `app/build.gradle.kts`. `versionCode` must increase on
every release; Android uses it, not `versionName`, to decide what counts as an upgrade.
