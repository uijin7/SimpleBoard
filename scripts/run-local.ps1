param(
    [string]$EnvFile = ".\local.env.ps1"
)

$ErrorActionPreference = "Stop"

$repoRoot = Split-Path -Parent $PSScriptRoot
Set-Location $repoRoot

if (Test-Path $EnvFile) {
    . $EnvFile
} else {
    Write-Host "Environment file not found: $EnvFile" -ForegroundColor Yellow
    Write-Host "Copy .\local.env.ps1.example to .\local.env.ps1 and fill in Supabase values." -ForegroundColor Yellow
}

$required = @("DB_URL", "DB_USERNAME", "DB_PASSWORD")
$missing = $required | Where-Object { [string]::IsNullOrWhiteSpace([Environment]::GetEnvironmentVariable($_)) }

if ($missing.Count -gt 0) {
    throw "Missing required environment variables: $($missing -join ', ')"
}

if ([string]::IsNullOrWhiteSpace($env:SPRING_PROFILES_ACTIVE)) {
    $env:SPRING_PROFILES_ACTIVE = "local"
}

if ([string]::IsNullOrWhiteSpace($env:JPA_DIALECT)) {
    $env:JPA_DIALECT = "org.hibernate.dialect.PostgreSQLDialect"
}

$env:GRADLE_USER_HOME = Join-Path $repoRoot ".gradle-user-home"

Write-Host "Starting SimpleBoard with Supabase PostgreSQL..." -ForegroundColor Cyan
Write-Host "Profile: $env:SPRING_PROFILES_ACTIVE" -ForegroundColor Cyan
Write-Host "DB URL: $env:DB_URL" -ForegroundColor Cyan

& .\gradlew.bat bootRun
