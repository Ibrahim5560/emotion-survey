import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ISystemServices, defaultValue } from 'app/shared/model/system-services.model';

export const ACTION_TYPES = {
  FETCH_SYSTEMSERVICES_LIST: 'systemServices/FETCH_SYSTEMSERVICES_LIST',
  FETCH_SYSTEMSERVICES: 'systemServices/FETCH_SYSTEMSERVICES',
  CREATE_SYSTEMSERVICES: 'systemServices/CREATE_SYSTEMSERVICES',
  UPDATE_SYSTEMSERVICES: 'systemServices/UPDATE_SYSTEMSERVICES',
  DELETE_SYSTEMSERVICES: 'systemServices/DELETE_SYSTEMSERVICES',
  RESET: 'systemServices/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ISystemServices>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type SystemServicesState = Readonly<typeof initialState>;

// Reducer

export default (state: SystemServicesState = initialState, action): SystemServicesState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_SYSTEMSERVICES_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SYSTEMSERVICES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_SYSTEMSERVICES):
    case REQUEST(ACTION_TYPES.UPDATE_SYSTEMSERVICES):
    case REQUEST(ACTION_TYPES.DELETE_SYSTEMSERVICES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_SYSTEMSERVICES_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SYSTEMSERVICES):
    case FAILURE(ACTION_TYPES.CREATE_SYSTEMSERVICES):
    case FAILURE(ACTION_TYPES.UPDATE_SYSTEMSERVICES):
    case FAILURE(ACTION_TYPES.DELETE_SYSTEMSERVICES):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_SYSTEMSERVICES_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_SYSTEMSERVICES):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_SYSTEMSERVICES):
    case SUCCESS(ACTION_TYPES.UPDATE_SYSTEMSERVICES):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_SYSTEMSERVICES):
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

const apiUrl = 'api/system-services';

// Actions

export const getEntities: ICrudGetAllAction<ISystemServices> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_SYSTEMSERVICES_LIST,
    payload: axios.get<ISystemServices>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ISystemServices> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SYSTEMSERVICES,
    payload: axios.get<ISystemServices>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ISystemServices> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SYSTEMSERVICES,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ISystemServices> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SYSTEMSERVICES,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ISystemServices> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SYSTEMSERVICES,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
