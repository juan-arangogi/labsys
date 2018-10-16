/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LabsysTestModule } from '../../../test.module';
import { TipoMuestraLabsysDetailComponent } from 'app/entities/tipo-muestra-labsys/tipo-muestra-labsys-detail.component';
import { TipoMuestraLabsys } from 'app/shared/model/tipo-muestra-labsys.model';

describe('Component Tests', () => {
    describe('TipoMuestraLabsys Management Detail Component', () => {
        let comp: TipoMuestraLabsysDetailComponent;
        let fixture: ComponentFixture<TipoMuestraLabsysDetailComponent>;
        const route = ({ data: of({ tipoMuestra: new TipoMuestraLabsys(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LabsysTestModule],
                declarations: [TipoMuestraLabsysDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TipoMuestraLabsysDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TipoMuestraLabsysDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.tipoMuestra).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
