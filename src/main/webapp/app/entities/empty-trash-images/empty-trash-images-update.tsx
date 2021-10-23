import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './empty-trash-images.reducer';
import { IEmptyTrashImages } from 'app/shared/model/empty-trash-images.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const EmptyTrashImagesUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const emptyTrashImagesEntity = useAppSelector(state => state.emptyTrashImages.entity);
  const loading = useAppSelector(state => state.emptyTrashImages.loading);
  const updating = useAppSelector(state => state.emptyTrashImages.updating);
  const updateSuccess = useAppSelector(state => state.emptyTrashImages.updateSuccess);
  const handleClose = () => {
    props.history.push('/empty-trash-images');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...emptyTrashImagesEntity,
      ...values,
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
          ...emptyTrashImagesEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="greenCityApp.emptyTrashImages.home.createOrEditLabel" data-cy="EmptyTrashImagesCreateUpdateHeading">
            Create or edit a EmptyTrashImages
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
                <ValidatedField name="id" required readOnly id="empty-trash-images-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Path" id="empty-trash-images-path" name="path" data-cy="path" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/empty-trash-images" replace color="info">
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

export default EmptyTrashImagesUpdate;
