import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './center.reducer';
import { ICenter } from 'app/shared/model/center.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICenterDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CenterDetail = (props: ICenterDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { centerEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="emotionSurveyApp.center.detail.title">Center</Translate> [<b>{centerEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="nameAr">
              <Translate contentKey="emotionSurveyApp.center.nameAr">Name Ar</Translate>
            </span>
          </dt>
          <dd>{centerEntity.nameAr}</dd>
          <dt>
            <span id="nameEn">
              <Translate contentKey="emotionSurveyApp.center.nameEn">Name En</Translate>
            </span>
          </dt>
          <dd>{centerEntity.nameEn}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="emotionSurveyApp.center.code">Code</Translate>
            </span>
          </dt>
          <dd>{centerEntity.code}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="emotionSurveyApp.center.status">Status</Translate>
            </span>
          </dt>
          <dd>{centerEntity.status}</dd>
        </dl>
        <Button tag={Link} to="/center" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/center/${centerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ center }: IRootState) => ({
  centerEntity: center.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CenterDetail);
