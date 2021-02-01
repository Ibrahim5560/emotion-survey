import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IMessageFeedback, defaultValue } from 'app/shared/model/message-feedback.model';

export const ACTION_TYPES = {
  FETCH_MESSAGEFEEDBACK_LIST: 'messageFeedback/FETCH_MESSAGEFEEDBACK_LIST',
  FETCH_MESSAGEFEEDBACK: 'messageFeedback/FETCH_MESSAGEFEEDBACK',
  CREATE_MESSAGEFEEDBACK: 'messageFeedback/CREATE_MESSAGEFEEDBACK',
  UPDATE_MESSAGEFEEDBACK: 'messageFeedback/UPDATE_MESSAGEFEEDBACK',
  DELETE_MESSAGEFEEDBACK: 'messageFeedback/DELETE_MESSAGEFEEDBACK',
  RESET: 'messageFeedback/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMessageFeedback>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type MessageFeedbackState = Readonly<typeof initialState>;

// Reducer

export default (state: MessageFeedbackState = initialState, action): MessageFeedbackState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_MESSAGEFEEDBACK_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MESSAGEFEEDBACK):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_MESSAGEFEEDBACK):
    case REQUEST(ACTION_TYPES.UPDATE_MESSAGEFEEDBACK):
    case REQUEST(ACTION_TYPES.DELETE_MESSAGEFEEDBACK):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_MESSAGEFEEDBACK_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MESSAGEFEEDBACK):
    case FAILURE(ACTION_TYPES.CREATE_MESSAGEFEEDBACK):
    case FAILURE(ACTION_TYPES.UPDATE_MESSAGEFEEDBACK):
    case FAILURE(ACTION_TYPES.DELETE_MESSAGEFEEDBACK):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_MESSAGEFEEDBACK_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_MESSAGEFEEDBACK):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_MESSAGEFEEDBACK):
    case SUCCESS(ACTION_TYPES.UPDATE_MESSAGEFEEDBACK):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_MESSAGEFEEDBACK):
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

const apiUrl = 'api/message-feedbacks';

// Actions

export const getEntities: ICrudGetAllAction<IMessageFeedback> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_MESSAGEFEEDBACK_LIST,
    payload: axios.get<IMessageFeedback>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IMessageFeedback> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MESSAGEFEEDBACK,
    payload: axios.get<IMessageFeedback>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IMessageFeedback> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MESSAGEFEEDBACK,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IMessageFeedback> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MESSAGEFEEDBACK,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMessageFeedback> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MESSAGEFEEDBACK,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
