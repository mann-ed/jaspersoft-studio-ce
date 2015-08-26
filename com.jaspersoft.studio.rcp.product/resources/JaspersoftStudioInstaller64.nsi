; Script generated by the HM NIS Edit Script Wizard.

!include "MUI2.nsh"
!define PRODUCT_NAME "TIBCO Jaspersoft Studio"
!define SHORT_PRODUCT_NAME "Jaspersoft Studio"
!define PRODUCT_PUBLISHER "TIBCO Software Inc."
!define PRODUCT_DIR_REGKEY "Software\Microsoft\Windows\CurrentVersion\App Paths\${PRODUCT_NAME}-${PRODUCT_VERSION}.exe"
!define PRODUCT_UNINST_KEY "Software\Microsoft\Windows\CurrentVersion\Uninstall\${PRODUCT_NAME}-${PRODUCT_VERSION}.exe"
!define PRODUCT_UNINST_ROOT_KEY "HKLM"
!define PRODUCT_STARTMENU_REGVAL "NSIS:StartMenuDir"
!define PRODUCT_ARCH "64bit"
!define ICONS_GROUP "TIBCO\${SHORT_PRODUCT_NAME} ${PRODUCT_VERSION} ${PRODUCT_ARCH}"

!include LogicLib.nsh
!include fileassoc.nsh
!include "nsProcess.nsh"

; MUI Settings
;--------------------------------
!define MUI_HEADERIMAGE
!define MUI_HEADERIMAGE_BITMAP "installer\installer_header.bmp" ; optional
!define MUI_ABORTWARNING
!define MUI_ICON "installer\jss_installer.ico"
!define MUI_UNICON "installer\jss_installer.ico"

; License page
!insertmacro MUI_PAGE_LICENSE "EPLv1-LICENSE.txt"

; Directory page
!insertmacro MUI_PAGE_DIRECTORY

; Instfiles page
!insertmacro MUI_PAGE_INSTFILES

; Finish page
!define MUI_WELCOMEFINISHPAGE_BITMAP "installer\installer_left.bmp"
!define MUI_FINISHPAGE_RUN "$INSTDIR\${SHORT_PRODUCT_NAME}.exe"
!insertmacro MUI_PAGE_FINISH

; Uninstaller pages
!insertmacro MUI_UNPAGE_INSTFILES

; Language files
!insertmacro MUI_LANGUAGE "English"

; MUI end
;--------------------------------

Name "${PRODUCT_NAME} ${PRODUCT_VERSION}"
OutFile "${OUTPUT_FILE_NAME}"
InstallDir "$PROGRAMFILES64\TIBCO\${SHORT_PRODUCT_NAME}-${PRODUCT_VERSION}"
InstallDirRegKey HKLM "${PRODUCT_DIR_REGKEY}" ""
ShowInstDetails show
ShowUnInstDetails show

Section "${PRODUCT_NAME}" SEC01
  SetShellVarContext all
  SetOutPath "$INSTDIR"
  SetOverwrite try
  File /r /x src "${INSTALLER_FILES_DIR}\*.*"

; Shortcuts
  CreateDirectory "$SMPROGRAMS\${ICONS_GROUP}"
  CreateShortCut "$SMPROGRAMS\${ICONS_GROUP}\${PRODUCT_NAME}-${PRODUCT_VERSION}.lnk" "$INSTDIR\${SHORT_PRODUCT_NAME}.exe"
  CreateShortCut "$DESKTOP\${PRODUCT_NAME}-${PRODUCT_VERSION}.lnk" "$INSTDIR\${SHORT_PRODUCT_NAME}.exe"
SectionEnd

Section -AdditionalIcons
  SetShellVarContext all
  SetOutPath $INSTDIR
  WriteIniStr "$SMPROGRAMS\${ICONS_GROUP}\${PRODUCT_NAME} site.url" "InternetShortcut" "URL" "${PRODUCT_WEB_SITE}"
  CreateShortCut "$SMPROGRAMS\${ICONS_GROUP}\Uninstall.lnk" "$INSTDIR\uninst.exe"
SectionEnd

Section -Post
  WriteUninstaller "$INSTDIR\uninst.exe"
  WriteRegStr HKLM "${PRODUCT_DIR_REGKEY}" "" "$INSTDIR\${SHORT_PRODUCT_NAME}.exe"
  WriteRegStr ${PRODUCT_UNINST_ROOT_KEY} "${PRODUCT_UNINST_KEY}" "DisplayName" "$(^Name)"
  WriteRegStr ${PRODUCT_UNINST_ROOT_KEY} "${PRODUCT_UNINST_KEY}" "UninstallString" "$INSTDIR\uninst.exe"
  WriteRegStr ${PRODUCT_UNINST_ROOT_KEY} "${PRODUCT_UNINST_KEY}" "DisplayIcon" "$INSTDIR\${SHORT_PRODUCT_NAME}.exe"
  WriteRegStr ${PRODUCT_UNINST_ROOT_KEY} "${PRODUCT_UNINST_KEY}" "DisplayVersion" "${PRODUCT_VERSION}"
  WriteRegStr ${PRODUCT_UNINST_ROOT_KEY} "${PRODUCT_UNINST_KEY}" "URLInfoAbout" "${PRODUCT_WEB_SITE}"
  WriteRegStr ${PRODUCT_UNINST_ROOT_KEY} "${PRODUCT_UNINST_KEY}" "Publisher" "${PRODUCT_PUBLISHER}"
  !insertmacro APP_ASSOCIATE "jrxml" "JaspersoftStudio.Jrxml" "Jrxml source file" "$INSTDIR\jaspersoftstudio.ico,0" "Open with ${PRODUCT_NAME}" "$INSTDIR\${SHORT_PRODUCT_NAME}.exe --launcher.openFile $\"%1$\""
  AccessControl::GrantOnFile "$INSTDIR\${SHORT_PRODUCT_NAME}.ini" "(S-1-5-32-545)" "GenericRead + GenericExecute + GenericWrite"
  AccessControl::GrantOnFile "$INSTDIR\dropins" "(S-1-5-32-545)" "GenericRead + GenericExecute + GenericWrite"
SectionEnd

Function un.onUninstSuccess
  HideWindow
  MessageBox MB_ICONINFORMATION|MB_OK "$(^Name) was successfully removed from your computer." /SD IDOK
FunctionEnd

Function un.onInit
  MessageBox MB_ICONQUESTION|MB_YESNO|MB_DEFBUTTON2 "Are you sure you want to completely remove $(^Name) and all of its components?" /SD IDYES IDYES +2
  Abort
  ${nsProcess::FindProcess} "${SHORT_PRODUCT_NAME}.exe" $R0
  ${If} $R0 == "0"
	# it's running
    MessageBox MB_ICONSTOP|MB_OK "An instance of the application is running. Please close and launch again uninstall."
    Abort
  ${EndIf}  
FunctionEnd

Section Uninstall

  SetShellVarContext all

  StrCmp "${ICONS_GROUP}" "" NO_SHORTCUTS
  RMDir /r /REBOOTOK $INSTDIR

  Delete "$SMPROGRAMS\${ICONS_GROUP}\Uninstall.lnk"
  Delete "$SMPROGRAMS\${ICONS_GROUP}\${PRODUCT_NAME} site.url"
  Delete "$DESKTOP\${PRODUCT_NAME}-${PRODUCT_VERSION}.lnk"
  Delete "$SMPROGRAMS\${ICONS_GROUP}\${PRODUCT_NAME}-${PRODUCT_VERSION}.lnk"
  !insertmacro APP_UNASSOCIATE "jrxml" "JaspersoftStudio.Jrxml"

  RMDir "$SMPROGRAMS\${ICONS_GROUP}"
  RMDir "$SMPROGRAMS\TIBCO"

  NO_SHORTCUTS:

  SetShellVarContext current

  DeleteRegKey ${PRODUCT_UNINST_ROOT_KEY} "${PRODUCT_UNINST_KEY}"
  DeleteRegKey HKLM "${PRODUCT_DIR_REGKEY}"
  SetAutoClose true

SectionEnd


# Uses $0
Function un.openLinkNewWindow
  Push $3
  Push $2
  Push $1
  Push $0
  ReadRegStr $0 HKCR "http\shell\open\command" ""
# Get browser path
    DetailPrint $0
  StrCpy $2 '"'
  StrCpy $1 $0 1
  StrCmp $1 $2 +2 # if path is not enclosed in " look for space as final char
    StrCpy $2 ' '
  StrCpy $3 1
  loop:
    StrCpy $1 $0 1 $3
    DetailPrint $1
    StrCmp $1 $2 found
    StrCmp $1 "" found
    IntOp $3 $3 + 1
    Goto loop

  found:
    StrCpy $1 $0 $3
    StrCmp $2 " " +2
      StrCpy $1 '$1"'

  Pop $0
  Exec '$1 $0'
  Pop $1
  Pop $2
  Pop $3
FunctionEnd