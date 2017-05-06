CREATE TABLE TCategories (
  ID BIGSERIAL PRIMARY KEY,
  NAME TEXT
);

CREATE TABLE TCitizens (
  ID BIGSERIAL PRIMARY KEY,
  NAME TEXT,
  SURNAME TEXT,
  EMAIL TEXT,
  BIRTHDAY DATE,
  RESIDENCE TEXT,
  NATIONALITY TEXT,
  DNI TEXT
);

CREATE TABLE TRestringedWords (
  ID BIGSERIAL PRIMARY KEY,
  WORD TEXT
);

CREATE TABLE TUsers (
  ID BIGSERIAL PRIMARY KEY,
  USERNAME TEXT,
  PASSWORD TEXT,
  ISADMIN BOOLEAN,
  CITIZEN_ID BIGSERIAL REFERENCES TCITIZENS (ID)
);

CREATE TABLE TSuggestions (
  ID BIGSERIAL PRIMARY KEY,
  CONTENTS TEXT,
  STATUS TEXT,
  CREATIONDATE DATE,
  FINALIZATIONDATE DATE,
  USER_ID BIGSERIAL REFERENCES TUSERS (ID),
  CATEGORY_ID BIGSERIAL REFERENCES TCATEGORIES (ID)
);

CREATE TABLE TSuggestionsVotes (
  SUGGESTION_ID BIGSERIAL NOT NULL,
  USER_ID BIGSERIAL NOT NULL,
  VOTE TEXT,
  CONSTRAINT PK_TSUGGESTIONSVOTES PRIMARY KEY (SUGGESTION_ID, USER_ID),
  CONSTRAINT FK_TSUGGESTIONSVOTES_SUGGESTION FOREIGN KEY (SUGGESTION_ID) REFERENCES TSUGGESTIONS (ID),
  CONSTRAINT FK_TSUGGESTIONSVOTES_USER FOREIGN KEY (USER_ID) REFERENCES TUSERS (ID)
);

CREATE TABLE TComments (
  ID BIGSERIAL PRIMARY KEY,
  CONTENTS TEXT,
  CREATIONDATE DATE,
  SUGGESTION_ID BIGSERIAL REFERENCES TSuggestions (ID),
  USER_ID BIGSERIAL REFERENCES TUsers (ID)
);

CREATE TABLE TCommentsVotes (
  COMMENT_ID BIGSERIAL NOT NULL,
  USER_ID BIGSERIAL NOT NULL,
  VOTE TEXT,
  CONSTRAINT PK_TCOMMENTVOTES PRIMARY KEY (COMMENT_ID, USER_ID),
  CONSTRAINT FK_TCOMMENTVOTES_COMMENT FOREIGN KEY (COMMENT_ID) REFERENCES TCOMMENTS (ID),
  CONSTRAINT FK_TCOMMENTVOTES_USER FOREIGN KEY (USER_ID) REFERENCES TUSERS (ID)
);
