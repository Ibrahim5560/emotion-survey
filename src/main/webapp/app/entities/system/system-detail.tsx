import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './system.reducer';
import { ISystem } from 'app/shared/model/system.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISystemDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SystemDetail = (props: ISystemDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { systemEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="emotionSurveyApp.system.detail.title">System</Translate> [<b>{systemEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="nameAr">
              <Translate contentKey="emotionSurveyApp.system.nameAr">Name Ar</Translate>
            </span>
          </dt>
          <dd>{systemEntity.nameAr}</dd>
          <dt>
            <span id="nameEn">
              <Translate contentKey="emotionSurveyApp.system.nameEn">Name En</Translate>
            </span>
          </dt>
          <dd>{systemEntity.nameEn}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="emotionSurveyApp.system.code">Code</Translate>
            </span>
          </dt>
          <dd>{systemEntity.code}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="emotionSurveyApp.system.status">Status</Translate>
            </span>
          </dt>
          <dd>{systemEntity.status}</dd>
        </dl>
        <Button tag={Link} to="/system" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/system/${systemEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ system }: IRootState) => ({
  systemEntity: system.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SystemDetail);
