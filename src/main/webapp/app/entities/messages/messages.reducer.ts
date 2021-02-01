import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IMessages, defaultValue } from 'app/shared/model/messages.model';

export const ACTION_TYPES = {
  FETCH_MESSAGES_LIST: 'messages/FETCH_MESSAGES_LIST',
  FETCH_MESSAGES: 'messages/FETCH_MESSAGES',
  CREATE_MESSAGES: 'messages/CREATE_MESSAGES',
  UPDATE_MESSAGES: 'messages/UPDATE_MESSAGES',
  DELETE_MESSAGES: 'messages/DELETE_MESSAGES',
  RESET: 'messages/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMessages>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type MessagesState = Readonly<typeof initialState>;

// Reducer

export default (state: MessagesState = initialState, action): MessagesState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_MESSAGES_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MESSAGES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_MESSAGES):
    case REQUEST(ACTION_TYPES.UPDATE_MESSAGES):
    case REQUEST(ACTION_TYPES.DELETE_MESSAGES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_MESSAGES_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MESSAGES):
    case FAILURE(ACTION_TYPES.CREATE_MESSAGES):
    case FAILURE(ACTION_TYPES.UPDATE_MESSAGES):
    case FAILURE(ACTION_TYPES.DELETE_MESSAGES):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_MESSAGES_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_MESSAGES):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_MESSAGES):
    case SUCCESS(ACTION_TYPES.UPDATE_MESSAGES):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_MESSAGES):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/messages';

// Actions

export const getEntities: ICrudGetAllAction<IMessages> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_MESSAGES_LIST,
    payload: axios.get<IMessages>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IMessages> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MESSAGES,
    payload: axios.get<IMessages>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IMessages> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MESSAGES,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IMessages> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MESSAGES,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMessages> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MESSAGES,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
