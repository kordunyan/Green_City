import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import authentication from './authentication';
import applicationProfile from './application-profile';

import administration from 'app/modules/administration/administration.reducer';
import userManagement from 'app/modules/administration/user-management/user-management.reducer';
import register from 'app/modules/account/register/register.reducer';
import activate from 'app/modules/account/activate/activate.reducer';
import password from 'app/modules/account/password/password.reducer';
import settings from 'app/modules/account/settings/settings.reducer';
import passwordReset from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import osbb from 'app/entities/osbb/osbb.reducer';
// prettier-ignore
import trashCompany from 'app/entities/trash-company/trash-company.reducer';
// prettier-ignore
import trashExportation from 'app/entities/trash-exportation/trash-exportation.reducer';
// prettier-ignore
import fullTrashImages from 'app/entities/full-trash-images/full-trash-images.reducer';
// prettier-ignore
import emptyTrashImages from 'app/entities/empty-trash-images/empty-trash-images.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const rootReducer = {
  authentication,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  osbb,
  trashCompany,
  trashExportation,
  fullTrashImages,
  emptyTrashImages,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
};

export default rootReducer;
