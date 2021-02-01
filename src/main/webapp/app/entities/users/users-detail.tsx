import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './users.reducer';
import { IUsers } from 'app/shared/model/users.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IUsersDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const UsersDetail = (props: IUsersDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { usersEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="emotionSurveyApp.users.detail.title">Users</Translate> [<b>{usersEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="nameAr">
              <Translate contentKey="emotionSurveyApp.users.nameAr">Name Ar</Translate>
            </span>
          </dt>
          <dd>{usersEntity.nameAr}</dd>
          <dt>
            <span id="nameEn">
              <Translate contentKey="emotionSurveyApp.users.nameEn">Name En</Translate>
            </span>
          </dt>
          <dd>{usersEntity.nameEn}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="emotionSurveyApp.users.code">Code</Translate>
            </span>
          </dt>
          <dd>{usersEntity.code}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="emotionSurveyApp.users.status">Status</Translate>
            </span>
          </dt>
          <dd>{usersEntity.status}</dd>
        </dl>
        <Button tag={Link} to="/users" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/users/${usersEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ users }: IRootState) => ({
  usersEntity: users.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(UsersDetail);
