import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ISystem, defaultValue } from 'app/shared/model/system.model';

export const ACTION_TYPES = {
  FETCH_SYSTEM_LIST: 'system/FETCH_SYSTEM_LIST',
  FETCH_SYSTEM: 'system/FETCH_SYSTEM',
  CREATE_SYSTEM: 'system/CREATE_SYSTEM',
  UPDATE_SYSTEM: 'system/UPDATE_SYSTEM',
  DELETE_SYSTEM: 'system/DELETE_SYSTEM',
  RESET: 'system/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ISystem>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type SystemState = Readonly<typeof initialState>;

// Reducer

export default (state: SystemState = initialState, action): SystemState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_SYSTEM_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SYSTEM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_SYSTEM):
    case REQUEST(ACTION_TYPES.UPDATE_SYSTEM):
    case REQUEST(ACTION_TYPES.DELETE_SYSTEM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_SYSTEM_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SYSTEM):
    case FAILURE(ACTION_TYPES.CREATE_SYSTEM):
    case FAILURE(ACTION_TYPES.UPDATE_SYSTEM):
    case FAILURE(ACTION_TYPES.DELETE_SYSTEM):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_SYSTEM_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_SYSTEM):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_SYSTEM):
    case SUCCESS(ACTION_TYPES.UPDATE_SYSTEM):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_SYSTEM):
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

const apiUrl = 'api/systems';

// Actions

export const getEntities: ICrudGetAllAction<ISystem> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_SYSTEM_LIST,
    payload: axios.get<ISystem>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ISystem> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SYSTEM,
    payload: axios.get<ISystem>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ISystem> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SYSTEM,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ISystem> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SYSTEM,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ISystem> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SYSTEM,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
