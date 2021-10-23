import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { ITrashCompany } from 'app/shared/model/trash-company.model';
import { getEntities as getTrashCompanies } from 'app/entities/trash-company/trash-company.reducer';
import { getEntity, updateEntity, createEntity, reset } from './osbb.reducer';
import { IOsbb } from 'app/shared/model/osbb.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const OsbbUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const trashCompanies = useAppSelector(state => state.trashCompany.entities);
  const osbbEntity = useAppSelector(state => state.osbb.entity);
  const loading = useAppSelector(state => state.osbb.loading);
  const updating = useAppSelector(state => state.osbb.updating);
  const updateSuccess = useAppSelector(state => state.osbb.updateSuccess);
  const handleClose = () => {
    props.history.push('/osbb');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getTrashCompanies({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...osbbEntity,
      ...values,
      trashCompany: trashCompanies.find(it => it.id.toString() === values.trashCompany.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...osbbEntity,
          trashCompany: osbbEntity?.trashCompany?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="greenCityApp.osbb.home.createOrEditLabel" data-cy="OsbbCreateUpdateHeading">
            Create or edit a Osbb
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="osbb-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Address" id="osbb-address" name="address" data-cy="address" type="text" />
              <ValidatedField label="Geo" id="osbb-geo" name="geo" data-cy="geo" type="text" />
              <ValidatedField label="Name" id="osbb-name" name="name" data-cy="name" type="text" />
              <ValidatedField id="osbb-trashCompany" name="trashCompany" data-cy="trashCompany" label="Trash Company" type="select">
                <option value="" key="0" />
                {trashCompanies
                  ? trashCompanies.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/osbb" replace color="info">
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

export default OsbbUpdate;
