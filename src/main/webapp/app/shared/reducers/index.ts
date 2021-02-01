import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale, { LocaleState } from './locale';
import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import system, {
  SystemState
} from 'app/entities/system/system.reducer';
// prettier-ignore
import systemServices, {
  SystemServicesState
} from 'app/entities/system-services/system-services.reducer';
// prettier-ignore
import center, {
  CenterState
} from 'app/entities/center/center.reducer';
// prettier-ignore
import users, {
  UsersState
} from 'app/entities/users/users.reducer';
// prettier-ignore
import messages, {
  MessagesState
} from 'app/entities/messages/messages.reducer';
// prettier-ignore
import messageFeedback, {
  MessageFeedbackState
} from 'app/entities/message-feedback/message-feedback.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly locale: LocaleState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly system: SystemState;
  readonly systemServices: SystemServicesState;
  readonly center: CenterState;
  readonly users: UsersState;
  readonly messages: MessagesState;
  readonly messageFeedback: MessageFeedbackState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  system,
  systemServices,
  center,
  users,
  messages,
  messageFeedback,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
});

export default rootReducer;
