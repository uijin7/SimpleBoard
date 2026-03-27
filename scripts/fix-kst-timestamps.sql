UPDATE post
SET posted_at = posted_at + INTERVAL '9 hours'
WHERE posted_at IS NOT NULL;

UPDATE member_account
SET created_at = created_at + INTERVAL '9 hours'
WHERE created_at IS NOT NULL;

UPDATE reply
SET replied_at = replied_at + INTERVAL '9 hours'
WHERE replied_at IS NOT NULL;
