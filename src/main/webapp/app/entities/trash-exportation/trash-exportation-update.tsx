import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IOsbb } from 'app/shared/model/osbb.model';
import { getEntities as getOsbbs } from 'app/entities/osbb/osbb.reducer';
import { getEntity, updateEntity, createEntity, reset } from './trash-exportation.reducer';
import { ITrashExportation } from 'app/shared/model/trash-exportation.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const TrashExportationUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const osbbs = useAppSelector(state => state.osbb.entities);
  const trashExportationEntity = useAppSelector(state => state.trashExportation.entity);
  const loading = useAppSelector(state => state.trashExportation.loading);
  const updating = useAppSelector(state => state.trashExportation.updating);
  const updateSuccess = useAppSelector(state => state.trashExportation.updateSuccess);
  const handleClose = () => {
    props.history.push('/trash-exportation');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getOsbbs({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.date = convertDateTimeToServer(values.date);

    const entity = {
      ...trashExportationEntity,
      ...values,
      osbb: osbbs.find(it => it.id.toString() === values.osbb.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          date: displayDefaultDateTime(),
        }
      : {
          ...trashExportationEntity,
          date: convertDateTimeFromServer(trashExportationEntity.date),
          osbb: trashExportationEntity?.osbb?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="greenCityApp.trashExportation.home.createOrEditLabel" data-cy="TrashExportationCreateUpdateHeading">
            Create or edit a TrashExportation
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField name="id" required readOnly id="trash-exportation-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Weight" id="trash-exportation-weight" name="weight" data-cy="weight" type="text" />
              <ValidatedField
                label="Date"
                id="trash-exportation-date"
                name="date"
                data-cy="date"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField label="Trash Type" id="trash-exportation-trash_type" name="trash_type" data-cy="trash_type" type="text" />
              <ValidatedField label="Is Wash" id="trash-exportation-is_wash" name="is_wash" data-cy="is_wash" check type="checkbox" />
              <ValidatedField id="trash-exportation-osbb" name="osbb" data-cy="osbb" label="Osbb" type="select">
                <option value="" key="0" />
                {osbbs
                  ? osbbs.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/trash-exportation" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default TrashExportationUpdate;
