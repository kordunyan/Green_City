import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './trash-exportation.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const TrashExportationDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const trashExportationEntity = useAppSelector(state => state.trashExportation.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="trashExportationDetailsHeading">TrashExportation</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{trashExportationEntity.id}</dd>
          <dt>
            <span id="weight">Weight</span>
          </dt>
          <dd>{trashExportationEntity.weight}</dd>
          <dt>
            <span id="date">Date</span>
          </dt>
          <dd>
            {trashExportationEntity.date ? <TextFormat value={trashExportationEntity.date} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="trash_type">Trash Type</span>
          </dt>
          <dd>{trashExportationEntity.trash_type}</dd>
          <dt>
            <span id="action">Action</span>
          </dt>
          <dd>{trashExportationEntity.action}</dd>
          <dt>Osbb</dt>
          <dd>{trashExportationEntity.osbb ? trashExportationEntity.osbb.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/trash-exportation" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/trash-exportation/${trashExportationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default TrashExportationDetail;
