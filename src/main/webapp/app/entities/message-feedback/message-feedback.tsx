import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './message-feedback.reducer';
import { IMessageFeedback } from 'app/shared/model/message-feedback.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

export interface IMessageFeedbackProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const MessageFeedback = (props: IMessageFeedbackProps) => {
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE), props.location.search)
  );

  const getAllEntities = () => {
    props.getEntities(paginationState.activePage - 1, paginationState.itemsPerPage, `${paginationState.sort},${paginationState.order}`);
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (props.location.search !== endURL) {
      props.history.push(`${props.location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(props.location.search);
    const page = params.get('page');
    const sort = params.get('sort');
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [props.location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === 'asc' ? 'desc' : 'asc',
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const { messageFeedbackList, match, loading, totalItems } = props;
  return (
    <div>
      <h2 id="message-feedback-heading">
        <Translate contentKey="emotionSurveyApp.messageFeedback.home.title">Message Feedbacks</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="emotionSurveyApp.messageFeedback.home.createLabel">Create new Message Feedback</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {messageFeedbackList && messageFeedbackList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('systemId')}>
                  <Translate contentKey="emotionSurveyApp.messageFeedback.systemId">System Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('centerId')}>
                  <Translate contentKey="emotionSurveyApp.messageFeedback.centerId">Center Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('systemServicesId')}>
                  <Translate contentKey="emotionSurveyApp.messageFeedback.systemServicesId">System Services Id</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('counter')}>
                  <Translate contentKey="emotionSurveyApp.messageFeedback.counter">Counter</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('trsId')}>
                  <Translate contentKey="emotionSurveyApp.messageFeedback.trsId">Trs Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('userId')}>
                  <Translate contentKey="emotionSurveyApp.messageFeedback.userId">User Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('message')}>
                  <Translate contentKey="emotionSurveyApp.messageFeedback.message">Message</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('status')}>
                  <Translate contentKey="emotionSurveyApp.messageFeedback.status">Status</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('feedback')}>
                  <Translate contentKey="emotionSurveyApp.messageFeedback.feedback">Feedback</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('applicantName')}>
                  <Translate contentKey="emotionSurveyApp.messageFeedback.applicantName">Applicant Name</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {messageFeedbackList.map((messageFeedback, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${messageFeedback.id}`} color="link" size="sm">
                      {messageFeedback.id}
                    </Button>
                  </td>
                  <td>{messageFeedback.systemId}</td>
                  <td>{messageFeedback.centerId}</td>
                  <td>{messageFeedback.systemServicesId}</td>
                  <td>{messageFeedback.counter}</td>
                  <td>{messageFeedback.trsId}</td>
                  <td>{messageFeedback.userId}</td>
                  <td>{messageFeedback.message}</td>
                  <td>{messageFeedback.status}</td>
                  <td>{messageFeedback.feedback}</td>
                  <td>{messageFeedback.applicantName}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${messageFeedback.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${messageFeedback.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${messageFeedback.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="danger"
                        size="sm"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="emotionSurveyApp.messageFeedback.home.notFound">No Message Feedbacks found</Translate>
            </div>
          )
        )}
      </div>
      {props.totalItems ? (
        <div className={messageFeedbackList && messageFeedbackList.length > 0 ? '' : 'd-none'}>
          <Row className="justify-content-center">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </Row>
          <Row className="justify-content-center">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={props.totalItems}
            />
          </Row>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

const mapStateToProps = ({ messageFeedback }: IRootState) => ({
  messageFeedbackList: messageFeedback.entities,
  loading: messageFeedback.loading,
  totalItems: messageFeedback.totalItems,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MessageFeedback);
